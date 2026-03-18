function calcular(op){
	var n1=parseFloat(valor1.value);
	var n2=parseFloat(valor2.value);
	var r;
	
	if(op=='+') r=n1+n2;
	if(op=='-') r=n1-n2;
	if(op=='*') r=n1*n2;
	if(op=='/') r=n1/n2;
	if(op=='media') r=((n1+n2)/2);
	if(op=='lucro') r=n1-n2;
	if(op=='total') r=n1*n2;
	
	resultado.innerHTML="resultado: "+r;
}