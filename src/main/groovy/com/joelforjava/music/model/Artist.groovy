package com.joelforjava.music.model

import groovy.transform.CompileStatic

@CompileStatic
class Artist {
    String name

    @Override
    String toString() {
        "$name"
    }
}
