package no.arktekk.siren;

import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public final class Rel implements StreamableIterable<String> {

    private final List<String> rels;

    private Rel(List<String> rels) {
        this.rels = unmodifiableList(rels);
    }

    public Rel(Iterable<String> rels) {
        this(StreamUtils.stream(rels).collect(Collectors.toList()));
    }

    public static Rel of(String rel, String... rels) {
        return new Rel(CollectionUtils.asList(rel, rels));
    }

    public boolean includes(Rel rel) {
        List<String> other = rel.rels;
        List<String> us = this.rels;
        return other.size() <= us.size() && StreamUtils.zip(us.stream(), other.stream(), String::equals).allMatch(t -> t);
    }

    public boolean includes(String rel) {
        return includes(Rel.of(rel));
    }

    public Rel add(String rel) {
        List<String> list = new ArrayList<>(this.rels);
        list.add(rel);
        return new Rel(list);
    }

    public Rel addAll(Rel rels) {
        List<String> list = new ArrayList<>(this.rels);
        list.addAll(rels.rels);
        return new Rel(list);
    }

    public Rel remove(String rel) {
        List<String> list = new ArrayList<>(this.rels);
        list.remove(rel);
        return new Rel(list);
    }

    public Iterator<String> iterator() {
        return rels.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rel strings = (Rel) o;

        return rels.equals(strings.rels);
    }

    @Override
    public int hashCode() {
        return rels.hashCode();
    }

    @Override
    public String toString() {
        return rels.toString();
    }
}
