/*
Pulsar
Copyright (C) 2013-2015 eBay Software Foundation
Dual licensed under the Apache 2.0 license and the GPL v2 license.  See LICENSE for full terms.
*/
package com.ebay.pulsar.collector.servlet;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.ebay.jetstream.event.support.ErrorManager;

@ManagedResource(objectName = "Event/Channel", description = "IngestServlet")
public class ServletStats {
    private String lastFailedRequest;
    private final ErrorManager errors = new ErrorManager();
    private final AtomicLong errorCount = new AtomicLong(0);
    private final AtomicLong ingestRequestCount = new AtomicLong(0);
    private final AtomicLong ingestBatchRequestCount = new AtomicLong(0);
    private final AtomicLong invalidRequestCount = new AtomicLong(0);

    @ManagedOperation
    public void cleanErrors() {
        errorCount.set(0);
        errors.clearErrorList();
        lastFailedRequest = null;
    }

    public long getIngestRequestCount() {
        return ingestRequestCount.get();
    }

    public long getBatchIngestRequestCount() {
        return ingestBatchRequestCount.get();
    }

    public long getErrorCount() {
        return errorCount.get();
    }

    @ManagedAttribute
    public ErrorManager getErrorManager() {
        return errors;
    }

    public String getLastFailedRequest() {
        return lastFailedRequest;
    }

    public void incIngestRequestCount() {
        ingestRequestCount.incrementAndGet();
    }

    public void incBatchIngestRequestCount() {
        ingestBatchRequestCount.incrementAndGet();
    }

    public void registerError(Throwable ex) {
        errorCount.incrementAndGet();
        errors.registerError(ex);
    }

    public void setLastFailedRequest(String lastFailedRequest) {
        this.lastFailedRequest = lastFailedRequest;
    }

    public void incInvalidRequestCount() {
        invalidRequestCount.incrementAndGet();
    }

    public long getInvalidRequestCount() {
        return invalidRequestCount.get();
    }

}
