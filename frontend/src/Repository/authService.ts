import instance from "../custom-axios/auth-axios";
import {Buffer} from "buffer";
import NotificationService from "./NotificationService";
import LocalStorageRepository from "./localStorageRepository";
import CredentialService from "./CredentialService";


const AuthService = {

    register: (name: string, surname: string, username: string, password: string) => {
        return instance.post("/identity/register", {
            name: name,
            surname: surname,
            username: username,
            password: password
        }).catch((err: any) => {
            NotificationService.error(err.message)
        })
    },

    fido_register: (username: string, name: string) => {
        return instance.post(`/identity/registration_start?username=${username}`)
            .then((response: any) => {
                CredentialService.createCredential(response.data, username, name)
                    .then((newCredentialInfo: any) => {
                        AuthService.finish_register(newCredentialInfo, username, response.data.registrationCeremonyId)
                            .then(() => {
                                NotificationService.success("Registration was successfully")
                            }).catch((err: any) => {
                            NotificationService.error(err.message);
                        })
                    }).catch((err: any) => {
                    NotificationService.error(err.message)
                })
            })
    },
    finish_register: (credentialResponse: any, username: string, registrationCeremonyId: any) => {
        return instance.post("/identity/registration_finish", {
            registrationCeremonyId: registrationCeremonyId,
            id: credentialResponse.id,
            type: credentialResponse.type,
            attestationObject: JSON.stringify(Buffer.from(credentialResponse.response.attestationObject)),
            username: username,
            clientDataHash: JSON.stringify(Buffer.from(credentialResponse.response.clientDataJSON))
        })
    },
    login: (username: string, password: string) => {
        let localStorageRepository = new LocalStorageRepository();
        return instance.post("/identity/login", {
            username: username,
            password: password
        }).then((response: any) => {
            localStorageRepository.saveUser(response.data)
        })
    },
    login_fido: () => {
        return instance.post("/identity/login_start")
            .then((response: any) => {
                CredentialService.getCredentials(response.data)
                    .then((assertionResponse: any) => {
                        AuthService.authenticate(assertionResponse, response.data.authenticationCeremonyId)
                            .then((res) => {
                                window.location.href = "/discussions"
                            }).catch((error: any) => {
                            NotificationService.error(error.message)
                        })
                    })
            }).catch((error: any) => {
                console.log(error, '!!!');
                NotificationService.error(error.message)
            })
    },
    authenticate: (resp: any, authenticationCeremonyId: any) => {
        let localStorageRepo = new LocalStorageRepository();
        return instance.post("/identity/login_finish", {
            authenticationCeremonyId: authenticationCeremonyId,
            id: JSON.stringify(Buffer.from(resp.rawId)),
            signature: JSON.stringify(Buffer.from(resp.response.signature)),
            authData: JSON.stringify(Buffer.from(resp.response.authenticatorData)),
            userHandle: JSON.stringify(Buffer.from(resp.response.userHandle)),
            clientDataJSON: JSON.stringify(Buffer.from(resp.response.clientDataJSON)),
            type: resp.type
        }).then((response: any) => {
            localStorageRepo.saveUser(response.data)
        })
    },
    logout: () => {
        let localStorageRepo = new LocalStorageRepository()
        localStorageRepo.removeUser()
    },
    getCurrentUser: () => {
        return instance.get("/identity",)
    },
    findById: (id: string) => {
        return instance.get(`/identity${id}`)
    }

}
export default AuthService;
