package com.example.latihanandroid.datamodels

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

    @field:SerializedName("role")
    val role: Any? = null,

    @field:SerializedName("active")
    val active: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: Any? = null,

    @field:SerializedName("current_team_id")
    val currentTeamId: Any? = null,

    @field:SerializedName("avatar")
    val avatar: String? = null,

    @field:SerializedName("type")
    val type: Int? = null,

    @field:SerializedName("token")
    val token: Any? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("two_factor_recovery_codes")
    val twoFactorRecoveryCodes: Any? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("profile_photo_path")
    val profilePhotoPath: Any? = null,

    @field:SerializedName("two_factor_secret")
    val twoFactorSecret: Any? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)
