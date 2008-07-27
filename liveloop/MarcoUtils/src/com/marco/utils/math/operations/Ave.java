package com.marco.utils.math.operations;

import com.marco.utils.math.Operation;

public class Ave {
	
	
	protected Operation operation;
	public Ave(Operation operation){
		this.operation=new Sum(operation);
	}
	public Object calculate(Object... params) {
		Object sum=operation.calculate(params);
		Object tot=Ops.LESS.calculate(params[2],params[1]);
		return Ops.DIV.calculate(sum,tot);
	}

}
