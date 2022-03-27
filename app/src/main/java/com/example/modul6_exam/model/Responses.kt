package com.example.modul6_exam.model

import com.google.gson.annotations.SerializedName

data class Responses(

	@field:SerializedName("card_holder")
	val cardHolder: String? = null,

	@field:SerializedName("card_number")
	val cardNumber: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("is_exsist")
	val isExsist: String? = null,

	@field:SerializedName("expired_date")
	val expiredDate: String? = null
)
