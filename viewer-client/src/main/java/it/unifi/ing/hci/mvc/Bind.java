package it.unifi.ing.hci.mvc;

@FunctionalInterface
public interface Bind<A> {
    void bind(A a);
}
