package com.weatherly.core.utils

import com.weatherly.core.R
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class ErrorMessageResult(
    val message: String?, // Custom error message, if available
    val fallbackResId: Int // Fallback resource ID
)

class ApiErrorHandler @Inject constructor() {

    /**
     * Returns a Pair of (custom error message, fallback resource ID).
     * If a specific error message is extracted, it will be in the first position.
     * Otherwise, fallback to the provided resource ID.
     */
    fun getErrorMessageResId(exception: Throwable): ErrorMessageResult {
        return when (exception) {
            is IOException -> ErrorMessageResult(null, R.string.error_no_internet)

            is HttpException -> {
                val errorBody = exception.response()?.errorBody()?.string()
                val extractedMessage = errorBody?.let { extractErrorMessage(it) }
                if (extractedMessage != null) {
                    ErrorMessageResult(extractedMessage, R.string.error_generic)
                } else {
                    ErrorMessageResult(null, R.string.error_something_went_wrong)
                }
            }

            else -> ErrorMessageResult(null, R.string.error_generic)
        }
    }

    /**
     * Extracts an error message string from the JSON response.
     * Returns null if parsing fails or the "message" key doesn't exist.
     */
    private fun extractErrorMessage(jsonString: String): String? {
        return try {
            val json = JSONObject(jsonString)
            json.getJSONObject("error").getString("message")
        } catch (e: Exception) {
            null
        }
    }
}
