package com.papero.capstoneexpert.core.base

import com.google.gson.annotations.SerializedName

data class BaseDataSourceResponse<T>(
	@SerializedName("status_code")
	val statusCode: Int?,

	@SerializedName("data")
	val data: T?,

	@SerializedName("status")
	val status: String?,
)
