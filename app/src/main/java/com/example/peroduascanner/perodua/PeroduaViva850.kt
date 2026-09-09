package com.example.peroduascanner.perodua

/**
 * Specific support for Perodua Viva 850 model.
 */
class PeroduaViva850 {
    fun supportedOperations(): List<String> {
        return listOf("readDTC", "clearDTC", "readDataStream")
    }
}
