package com.order.brunoestevam.dto;

public enum OrderStatusEnum {

	CREATED("cre", "created", "O item está ativo e disponível."),
	INACTIVE("ina", "inactive", "O item está inativo e não disponível."),
	PENDING("pen", "pending", "O item está aguardando alguma ação."),
	COMPLETED("con", "completed", "O item foi concluído.");

	private final String code;
	private final String name;
	private final String description;

	OrderStatusEnum(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
