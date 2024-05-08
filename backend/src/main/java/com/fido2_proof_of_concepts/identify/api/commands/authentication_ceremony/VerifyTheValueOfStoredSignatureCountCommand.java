package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import lombok.Getter;

/**
 * Verify the value of stored signature count command.
 */
@Getter
public class VerifyTheValueOfStoredSignatureCountCommand extends AbstractCeremonyCommand {

    private final int storedSignatureCount;

    /**
     * Constructor.
     *
     * @param id                   authentication ceremony id for authentication ceremony aggregate
     * @param storedSignatureCount stored signature count.
     */
    public VerifyTheValueOfStoredSignatureCountCommand(Identifier id, int storedSignatureCount) {
        super(id);
        this.storedSignatureCount = storedSignatureCount;
    }
}
