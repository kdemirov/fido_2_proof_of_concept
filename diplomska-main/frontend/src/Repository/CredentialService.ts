import {Buffer} from "buffer";

const CredentialService = {
    getCredentials: (response: any) => {
        return navigator.credentials.get({
            publicKey: {
                challenge: Buffer.from(response.challenge),
                timeout: response.timeout,
                rpId: "localhost",
                userVerification: "required"
            }
        })
    },
    createCredential: (response: any, username: string, name: string) => {
        return navigator.credentials.create({
            'publicKey': {
                challenge: Uint8Array.from(response.challenge, (c: any) => c.charCodeAt(0)),
                rp: {
                    id: response.rp.id,
                    name: "Identity access"
                },
                user: {
                    id: Uint8Array.from(response.user.id, (c: any) => c.charCodeAt(0)),
                    displayName: name,
                    name: username
                },
                pubKeyCredParams: [{
                    type: "public-key",
                    alg: -7
                }],
                timeout: response.timeout,
                authenticatorSelection: {
                    authenticatorAttachment: "cross-platform",
                    userVerification: "discouraged"
                },
                attestation: "direct"
            }
        })
    }
}

export default CredentialService;
