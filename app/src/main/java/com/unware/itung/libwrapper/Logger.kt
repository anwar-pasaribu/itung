package com.unware.itung.libwrapper

import timber.log.Timber

class Logger {
    companion object {
        fun info(infoMsg: String) {
            Timber.i(infoMsg)
        }

        fun error(errorMsg: String) {
            Timber.e(errorMsg)
        }

        fun warn(warnMsg: String) {
            Timber.w(warnMsg)
        }
    }
}