package org.itcluster11.keywords;

import java.util.Collection;

public interface IKeywordRegistry {
    boolean register(Keyword keyword);

    boolean registerAll(Keyword... keywords);

    boolean deregister(Keyword keyword);

    boolean deregisterAll(Keyword... keywords);

    Collection<Keyword> getRegisteredKeywords();

    Keyword getRegisteredKeyword(String name);
}
