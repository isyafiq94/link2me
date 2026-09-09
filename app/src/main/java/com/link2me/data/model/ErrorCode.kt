package com.link2me.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * OBD-II Diagnostic Trouble Code (DTC)
 */
@Entity(tableName = "error_codes")
data class ErrorCode(
    @PrimaryKey
    val code: String,  // e.g., "P0101"
    val description: String,
    val severity: ErrorSeverity,
    val system: String,  // Engine, Transmission, etc.
    val affectsPerformance: Boolean = false,
    val malaysianVehicleNote: String? = null
)

enum class ErrorSeverity {
    INFO,
    WARNING,
    CRITICAL
}

/**
 * Malaysian vehicle error code database
 */
object MalaysianErrorCodes {
    val CODES = mapOf(
        "P0101" to ErrorCode(
            code = "P0101",
            description = "Mass Air Flow (MAF) Sensor Range/Performance",
            severity = ErrorSeverity.WARNING,
            system = "Engine",
            affectsPerformance = true,
            malaysianVehicleNote = "Common in high-humidity tropical climate"
        ),
        "P0303" to ErrorCode(
            code = "P0303",
            description = "Cylinder 3 Misfire Detected",
            severity = ErrorSeverity.CRITICAL,
            system = "Engine",
            affectsPerformance = true
        ),
        "P0420" to ErrorCode(
            code = "P0420",
            description = "Catalyst System Efficiency Below Threshold",
            severity = ErrorSeverity.WARNING,
            system = "Emissions",
            affectsPerformance = false,
            malaysianVehicleNote = "May fail roadworthiness test"
        ),
        "P0128" to ErrorCode(
            code = "P0128",
            description = "Coolant Thermostat (Coolant Temp Regulating) Malfunction",
            severity = ErrorSeverity.WARNING,
            system = "Cooling",
            affectsPerformance = true
        ),
        "P0335" to ErrorCode(
            code = "P0335",
            description = "Crankshaft Position Sensor Circuit",
            severity = ErrorSeverity.CRITICAL,
            system = "Engine",
            affectsPerformance = true,
            malaysianVehicleNote = "Vehicle may not start"
        )
    )

    fun getCode(code: String): ErrorCode? {
        return CODES[code]
    }

    fun getAllCodes(): List<ErrorCode> {
        return CODES.values.toList()
    }
}
