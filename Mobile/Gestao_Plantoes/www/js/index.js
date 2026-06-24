document.addEventListener('deviceready', onDeviceReady, false);

// Global State
let currentUser = null;

function onDeviceReady() {
    console.log('App Skala Iniciado (Cordova ' + cordova.platformId + ')');
    initApp();
}

// Fallback for browser testing (if deviceready doesn't fire)
if (!window.cordova) {
    window.addEventListener('DOMContentLoaded', initApp);
}

function initApp() {
    initFirebase();

    // Setup Login Form
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    // Setup Register Form
    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }

    // Setup Links
    const linkRegister = document.getElementById('link-register');
    if (linkRegister) {
        linkRegister.addEventListener('click', (e) => {
            e.preventDefault();
            navigateTo('view-register');
        });
    }

    const linkLogin = document.getElementById('link-login');
    if (linkLogin) {
        linkLogin.addEventListener('click', (e) => {
            e.preventDefault();
            navigateTo('view-login');
        });
    }

    const linkForgot = document.getElementById('link-forgot-pwd');
    if (linkForgot) {
        linkForgot.addEventListener('click', handlePasswordReset);
    }

    // Setup Guest Access
    const btnGuest = document.getElementById('btn-guest');
    if (btnGuest) {
        btnGuest.addEventListener('click', handleGuestLogin);
    }

    // Setup Logout Button
    const btnLogout = document.getElementById('btn-logout');
    if (btnLogout) {
        btnLogout.addEventListener('click', handleLogout);
    }

    // Setup Bottom Navigation
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        item.addEventListener('click', (e) => {
            // Remove active from all
            navItems.forEach(nav => nav.classList.remove('active'));
            // Add active to clicked
            const currentItem = e.currentTarget;
            currentItem.classList.add('active');
            
            // Navigate
            const targetView = currentItem.getAttribute('data-target');
            navigateTo(targetView);
        });
    });

    // Setup Modal Actions
    const btnNewShift = document.getElementById('btn-new-shift');
    if (btnNewShift) {
        btnNewShift.addEventListener('click', () => {
            const modal = document.getElementById('modal-new-shift');
            if (modal) {
                modal.classList.remove('hidden');
            }
        });
    }

    const btnCloseModal = document.getElementById('btn-close-modal');
    if (btnCloseModal) {
        btnCloseModal.addEventListener('click', () => {
            const modal = document.getElementById('modal-new-shift');
            if (modal) {
                modal.classList.add('hidden');
            }
        });
    }

    const formNewShift = document.getElementById('form-new-shift');
    if (formNewShift) {
        formNewShift.addEventListener('submit', handleCreateShift);
    }

    // If the home view is active by default, load shifts from database.
    if (document.getElementById('view-home')?.classList.contains('active')) {
        fetchShifts();
    }
}

function initFirebase() {
    if (typeof firebase === 'undefined') {
        console.error('Firebase SDK não foi carregado. Verifique index.html.');
        return;
    }

    if (!window.firebaseDatabase && firebase.database) {
        window.firebaseDatabase = firebase.database();
    }
    if (!window.firebaseAuth && firebase.auth) {
        window.firebaseAuth = firebase.auth();
    }

    // Listen for auth state changes
    if (window.firebaseAuth) {
        window.firebaseAuth.onAuthStateChanged(user => {
            if (user) {
                // User is signed in.
                window.firebaseDatabase.ref(`users/${user.uid}`).once('value')
                    .then(snapshot => {
                        const userData = snapshot.val();
                        currentUser = {
                            uid: user.uid,
                            email: user.email,
                            role: userData ? userData.role : 'funcionario',
                            name: userData ? userData.name : user.email.split('@')[0]
                        };
                        console.log('User signed in:', currentUser);
                        updateUIForRole();
                        navigateTo('view-home');
                        document.getElementById('bottom-nav').classList.remove('hidden');
                    });
            } else {
                // No user is signed in.
                if (!currentUser || currentUser.role !== 'visitante') {
                    currentUser = null;
                    navigateTo('view-login');
                    document.getElementById('bottom-nav').classList.add('hidden');
                }
            }
        });
    }
}

function fetchShifts() {
    const shiftsList = document.querySelector('.shifts-list');
    if (!shiftsList) return;

    if (!window.firebaseDatabase) {
        shiftsList.innerHTML = '<p class="load-error">Não foi possível conectar ao banco de dados.</p>';
        return;
    }

    shiftsList.innerHTML = '<p class="loading">Carregando plantões...</p>';

    window.firebaseDatabase.ref('shifts').once('value')
        .then(snapshot => {
            shiftsList.innerHTML = '';

            if (!snapshot.exists()) {
                shiftsList.innerHTML = '<p class="no-data">Nenhum plantão encontrado no banco de dados.</p>';
                return;
            }

            snapshot.forEach(childSnapshot => {
                const shift = childSnapshot.val();
                const card = createShiftCard(childSnapshot.key, shift);
                shiftsList.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Erro ao ler plantões:', error);
            shiftsList.innerHTML = '<p class="load-error">Erro ao carregar plantões. Veja o console.</p>';
        });
}

function createShiftCard(id, shift) {
    const card = document.createElement('div');
    card.className = 'shift-card glass-panel';

    const status = shift.status || 'Disponível';
    const statusClass = status.toLowerCase().includes('confirm') ? 'status-confirmed' : 'status-available';
    const dateText = shift.date || shift.dateTime || 'Sem data';
    const titleText = shift.title || shift.name || 'Plantão';
    const durationText = shift.duration || shift.period || 'N/A';
    const locationText = shift.location || shift.place || 'Local não informado';
    const extraText = shift.desc || shift.extra || shift.subtitle || '';

    card.innerHTML = `
        <div class="shift-header">
            <span class="shift-date">${dateText}</span>
            <span class="status-badge ${statusClass}">${status}</span>
        </div>
        <h4 class="shift-title">${titleText}</h4>
        <div class="shift-details">
            <p><span class="material-symbols-rounded">schedule</span> ${durationText}</p>
            <p><span class="material-symbols-rounded">location_on</span> ${locationText}</p>
            ${extraText ? `<p><span class="material-symbols-rounded">info</span> ${extraText}</p>` : ''}
        </div>
        <div class="shift-actions">
        </div>
    `;

    const actionsContainer = card.querySelector('.shift-actions');

    if (status === 'Disponível' && currentUser && currentUser.role !== 'visitante') {
        const btnTake = document.createElement('button');
        btnTake.className = 'btn-primary';
        btnTake.textContent = 'Assumir Plantão';
        btnTake.onclick = () => handleTakeShift(id, shift);
        actionsContainer.appendChild(btnTake);
    } else {
        const btnDetails = document.createElement('button');
        btnDetails.className = 'btn-outline';
        btnDetails.textContent = 'Ver detalhes';
        actionsContainer.appendChild(btnDetails);
    }

    return card;
}

function handleTakeShift(shiftId, shiftData) {
    if (!window.firebaseDatabase || !currentUser) return;
    
    if (confirm(`Deseja assumir o plantão: ${shiftData.title}?`)) {
        window.firebaseDatabase.ref(`shifts/${shiftId}`).update({
            status: 'Confirmado',
            assigneeEmail: currentUser.email,
            assigneeName: currentUser.name,
            assigneeUid: currentUser.uid || null
        }).then(() => {
            alert('Plantão assumido com sucesso!');
            fetchShifts();
            if (document.getElementById('view-calendar')?.classList.contains('active')) {
                fetchMySchedule();
            }
        }).catch(err => {
            console.error('Erro ao assumir plantão', err);
            alert('Erro ao assumir plantão.');
        });
    }
}

function handleCreateShift(e) {
    e.preventDefault();
    if (!window.firebaseDatabase) {
        alert('Banco de dados não conectado.');
        return;
    }

    const title = document.getElementById('shift-title').value;
    const date = document.getElementById('shift-date').value;
    const duration = document.getElementById('shift-duration').value;
    const location = document.getElementById('shift-location').value;
    const desc = document.getElementById('shift-desc').value;

    const btnSubmit = e.target.querySelector('button[type="submit"]');
    const originalText = btnSubmit.textContent;
    btnSubmit.textContent = 'Criando...';
    btnSubmit.disabled = true;

    const newShift = {
        title,
        date,
        duration,
        location,
        desc,
        status: 'Disponível',
        createdBy: currentUser ? currentUser.email : 'desconhecido',
        createdAt: new Date().toISOString()
    };

    window.firebaseDatabase.ref('shifts').push(newShift)
        .then(() => {
            alert('Turno criado com sucesso!');
            document.getElementById('form-new-shift').reset();
            document.getElementById('modal-new-shift').classList.add('hidden');
            fetchShifts(); // Atualizar a lista
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
        })
        .catch(err => {
            console.error('Erro ao criar turno:', err);
            alert('Erro ao criar turno.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
        });
}

// Function para Aprovar Trocas (estruturada e comentada)
/*
function handleApproveSwaps() {
    // Lógica para listar solicitações de troca e o gestor aprovar ou negar
    // Exemplo: ler de um nó 'swap_requests', e ao aprovar, atualizar o 'assignee' do turno
}
*/

function handleLogin(e) {
    e.preventDefault();
    
    const emailInput = document.getElementById('email').value;
    const passwordInput = document.getElementById('password').value;
    const btnSubmit = e.target.querySelector('button[type="submit"]');
    const originalText = btnSubmit.textContent;
    btnSubmit.textContent = 'Entrando...';
    btnSubmit.disabled = true;

    if (window.firebaseAuth) {
        window.firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput)
            .then(() => {
                btnSubmit.textContent = originalText;
                btnSubmit.disabled = false;
            })
            .catch((error) => {
                console.error('Login error:', error);
                alert('Erro ao fazer login: ' + error.message);
                btnSubmit.textContent = originalText;
                btnSubmit.disabled = false;
            });
    } else {
        alert('Firebase não configurado.');
        btnSubmit.textContent = originalText;
        btnSubmit.disabled = false;
    }
}

function handleRegister(e) {
    e.preventDefault();
    
    const nameInput = document.getElementById('reg-name').value;
    const emailInput = document.getElementById('reg-email').value;
    const passwordInput = document.getElementById('reg-password').value;
    const btnSubmit = e.target.querySelector('button[type="submit"]');
    const originalText = btnSubmit.textContent;
    btnSubmit.textContent = 'Cadastrando...';
    btnSubmit.disabled = true;

    if (window.firebaseAuth) {
        window.firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
            .then((userCredential) => {
                const user = userCredential.user;
                let role = 'funcionario';
                if (emailInput.toLowerCase().includes('gestor') || emailInput.toLowerCase().includes('matheus') || emailInput.toLowerCase().includes('fellipe')) {
                    role = 'gestor';
                }
                
                const userData = {
                    name: nameInput,
                    email: emailInput,
                    role: role,
                    createdAt: new Date().toISOString()
                };
                
                return window.firebaseDatabase.ref(`users/${user.uid}`).set(userData);
            })
            .then(() => {
                btnSubmit.textContent = originalText;
                btnSubmit.disabled = false;
            })
            .catch((error) => {
                console.error('Register error:', error);
                alert('Erro ao criar conta: ' + error.message);
                btnSubmit.textContent = originalText;
                btnSubmit.disabled = false;
            });
    } else {
        alert('Firebase não configurado.');
        btnSubmit.textContent = originalText;
        btnSubmit.disabled = false;
    }
}

function handlePasswordReset(e) {
    e.preventDefault();
    const emailInput = document.getElementById('email').value;
    if (!emailInput) {
        alert('Por favor, preencha o campo de e-mail na tela de login para redefinir a senha.');
        return;
    }
    
    if (window.firebaseAuth) {
        window.firebaseAuth.sendPasswordResetEmail(emailInput)
            .then(() => {
                alert('E-mail de redefinição de senha enviado para: ' + emailInput);
            })
            .catch((error) => {
                console.error('Password reset error:', error);
                alert('Erro: ' + error.message);
            });
    }
}

function handleGuestLogin(e) {
    e.preventDefault();
    
    currentUser = {
        email: '',
        role: 'visitante',
        name: 'Visitante'
    };
    
    console.log('Logged in as Guest:', currentUser);
    
    updateUIForRole();
    navigateTo('view-home');
    document.getElementById('bottom-nav').classList.remove('hidden');
}

function navigateTo(viewId) {
    // Hide all views
    const views = document.querySelectorAll('.view');
    views.forEach(view => {
        view.classList.remove('active');
    });
    
    // Show target view
    const target = document.getElementById(viewId);
    if (target) {
        target.classList.add('active');
    } else {
        console.warn('View not found:', viewId);
        // Fallback to home if trying to navigate to a not-yet-implemented view
        document.getElementById('view-home').classList.add('active');
    }

    if (viewId === 'view-home') {
        fetchShifts();
    }
    if (viewId === 'view-calendar') {
        fetchMySchedule();
    }
    if (viewId === 'view-manage') {
        fetchTeam();
    }
    if (viewId === 'view-profile') {
        updateProfileSection();
    }
}

function updateUIForRole() {
    if (!currentUser) return;
    
    // Update header info
    document.getElementById('user-name').textContent = currentUser.name;
    let roleDisplay = 'Funcionário';
    if (currentUser.role === 'gestor') roleDisplay = 'Gestor';
    else if (currentUser.role === 'visitante') roleDisplay = 'Visitante';
    document.getElementById('user-role').textContent = roleDisplay;
    
    // Update profile section content
    updateProfileSection();
    
    // Show/Hide Gestor specific elements
    const gestorElements = document.querySelectorAll('.gestor-only');
    gestorElements.forEach(el => {
        if (currentUser.role === 'gestor') {
            el.style.display = ''; // Reset display (usually block/flex)
        } else {
            el.style.display = 'none';
        }
    });
}

function updateProfileSection() {
    if (!currentUser) return;
    const profileName = document.getElementById('profile-name');
    const profileEmail = document.getElementById('profile-email');
    const profileRole = document.getElementById('profile-role');

    if (profileName) profileName.textContent = currentUser.name;
    if (profileEmail) profileEmail.textContent = currentUser.email || 'Sem e-mail';
    if (profileRole) profileRole.textContent = currentUser.role === 'gestor' ? 'Gestor' : currentUser.role === 'visitante' ? 'Visitante' : 'Funcionário';
}

function saveUserToDatabase(user) {
    if (!window.firebaseDatabase) {
        console.warn('Firebase Database não está disponível para salvar o usuário.');
        return;
    }

    const safeKey = user.email
        ? user.email.replace(/[.#$\[\]]/g, '_')
        : `visitante_${Date.now()}`;

    const userData = {
        name: user.name,
        email: user.email || 'visitante',
        role: user.role,
        lastLogin: new Date().toISOString()
    };

    window.firebaseDatabase.ref(`users/${safeKey}`).set(userData)
        .then(() => {
            console.log('Usuário gravado no Firebase:', userData);
        })
        .catch(error => {
            console.error('Erro ao salvar usuário no Firebase:', error);
        });
}

function handleLogout() {
    if (window.firebaseAuth && window.firebaseAuth.currentUser) {
        window.firebaseAuth.signOut().catch((error) => {
            console.error('Sign out error', error);
        });
    } else {
        currentUser = null;
        navigateTo('view-login');
        document.getElementById('bottom-nav').classList.add('hidden');
    }
}
