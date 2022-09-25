package dev.caiosantesso.aws.reporter.api;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.FunctionConfiguration;
import software.amazon.awssdk.services.lambda.model.Runtime;
import software.amazon.awssdk.services.lambda.paginators.ListVersionsByFunctionIterable;

import java.util.Collection;
import java.util.stream.Stream;

public class LambdasEndpoint implements AutoCloseable {

    private final LambdaClient client;

    public record Lambda(String name, Runtime runtime, String version, Long codeSize) {
        public String toCsvRow() {
            return "%s,%s,%s,%d".formatted(name(), runtime(), version(), codeSize());
        }
    }

    public LambdasEndpoint() {
        this.client = LambdaClient
                .builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public Collection<Lambda> lambdas() {
        return allLambdasStream()
                .map(LambdasEndpoint::mapToRecord)
                .toList();
    }


    private Stream<FunctionConfiguration> allLambdasStream() {
        return client
                .listFunctionsPaginator()
                .functions()
                .stream();
    }

    public Collection<Lambda> lambdaVersions() {
        return allLambdasStream()
                .map(FunctionConfiguration::functionName)
                .map(name -> client.listVersionsByFunctionPaginator(builder -> builder.functionName(name)))
                .map(ListVersionsByFunctionIterable::versions)
                .flatMap(SdkIterable::stream)
                .map(LambdasEndpoint::mapToRecord)
                .toList();
    }

    private static Lambda mapToRecord(FunctionConfiguration fc) {
        return new Lambda(fc.functionName(), fc.runtime(), fc.version(), fc.codeSize());
    }

    @Override
    public void close() {
        client.close();
    }
}
