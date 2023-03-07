package de.chojo.nexus.requests.v1.search.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.PageAssetXO;
import de.chojo.nexus.requests.v1.search.ASearchRequest;

import java.util.concurrent.CompletableFuture;

public class SearchRequest extends ASearchRequest<SearchRequest, PageAssetXO> {
    public SearchRequest(NexusRestImpl rest) {
        super(rest, PageAssetXO.class);
        path("v1","search","assets");
    }

    @Override
    public CompletableFuture<PageAssetXO> queue() {
        return queueGetAndMap();
    }

    @Override
    public PageAssetXO complete() {
        return completeGetAndMap();
    }
}
