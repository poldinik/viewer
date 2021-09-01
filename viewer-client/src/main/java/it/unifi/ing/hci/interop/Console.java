package it.unifi.ing.hci.interop;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name="console")
public abstract class Console {

    private Console() {};
    public static native void log(Object ms);
    public static native void clear();

}
