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
    // Setup Login Form
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    // Setup Guest Access
    const btnGuest = document.getElementById('btn-guest');
    if (btnGuest) {
        btnGuest.addEventListener('click', handleGuestLogin);
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
}

function handleLogin(e) {
    e.preventDefault();
    
    const emailInput = document.getElementById('email').value;
    const passwordInput = document.getElementById('password').value;
    
    // Simulação Básica de Login
    // Se o email conter a palavra "gestor", entra como gestor.
    let role = 'funcionario';
    let name = 'Funcionário Padrão';
    
    if (emailInput.toLowerCase().includes('gestor')) {
        role = 'gestor';
        name = 'Gestor Admin';
    } else if (emailInput.toLowerCase().includes('matheus') || emailInput.toLowerCase().includes('fellipe')) {
        role = 'gestor';
        name = emailInput.split('@')[0];
        // Capitalize first letter
        name = name.charAt(0).toUpperCase() + name.slice(1);
    }
    
    currentUser = {
        email: emailInput,
        role: role,
        name: name
    };
    
    console.log('Logged in as:', currentUser);
    
    updateUIForRole();
    
    // Navigate to Home
    navigateTo('view-home');
    
    // Show bottom nav
    document.getElementById('bottom-nav').classList.remove('hidden');
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
    
    // Navigate to Home
    navigateTo('view-home');
    
    // Show bottom nav
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
}

function updateUIForRole() {
    if (!currentUser) return;
    
    // Update header info
    document.getElementById('user-name').textContent = currentUser.name;
    let roleDisplay = 'Funcionário';
    if (currentUser.role === 'gestor') roleDisplay = 'Gestor';
    else if (currentUser.role === 'visitante') roleDisplay = 'Visitante';
    document.getElementById('user-role').textContent = roleDisplay;
    
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
