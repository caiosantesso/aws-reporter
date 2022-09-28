module aws.reporter {
    requires java.base;
    requires java.logging;
    requires info.picocli;
    requires software.amazon.awssdk.services.lambda;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.core;

    opens dev.caiosantesso.aws.reporter.cli to info.picocli;
}
