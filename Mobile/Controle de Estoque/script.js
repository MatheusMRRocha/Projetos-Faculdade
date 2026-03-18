function calcular(){
	var qAtua = 0;
	var qVendida = 0;
	var qAtual = parseFloat(document.getElementById("qAtual").value);
	var qVendida = parseFloat(document.getElementById("qVendida").value);
	var r;
	if(qAtual >= qVendida){
		r = qAtual - qVendida;
		resultado.innerHTML="Quantidade do Estoque Atualizado: "+r;
	}else{
		resultado.innerHTML="Quantidade maior de vendas do que disponiveis no estoque";
	}
}
function limpar(){
	document.getElementById("qAtual").value = "";
	document.getElementById("qVendida").value = "";
	document.getElementById("resultado").value = "";
}