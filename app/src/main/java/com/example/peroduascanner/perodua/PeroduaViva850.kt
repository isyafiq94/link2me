package com.example.peroduascanner.perodua

/**
 * Specific support for Perodua Viva 850 model.
 * This class lists the supported operations and can contain model-specific translations.
 */
class PeroduaViva850 {
    fun supportedOperations(): List<String> {
        return listOf("readDTC", "clearDTC", "readDataStream")
    }
}
