import instance from "../custom-axios/auth-axios";
import {Buffer} from "buffer";
import NotificationService from "./NotificationService";
import LocalStorageRepository from "./localStorageRepository";
import CredentialService from "./CredentialService";


const AuthService = {

    register: (name: string, surname: string, username: string, password: string) => {
        return instance.post("/register", {
            name: name,
            surname: surname,
            username: username,
            password: password
        }).catch((err: any) => {
            NotificationService.error(err.message)
        })
    },
    fido_register: (username: string, name: string) => {
        return instance.post(`/registration_start?username=${username}`)
            .then((response: any) => {
                CredentialService.createCredential(response.data, username, name)
                    .then((newCredentialInfo: any) => {
                        AuthService.finish_register(newCredentialInfo, username)
                            .then(() => {
                                NotificationService.success("Registration was successfully")
                                window.location.href = "/discussions"
                            }).catch((err: any) => {
                            NotificationService.error(err.message);
                        })
                    }).catch((err: any) => {
                    NotificationService.error(err.message)
                })
            })
    },
    finish_register: (credentialResponse: any, username: string) => {
        return instance.post("/registration_finish", {
            id: credentialResponse.id,
            type: credentialResponse.type,
            attestationObject: JSON.stringify(Buffer.from(credentialResponse.response.attestationObject)),
            username: username,
            clientDataHash: JSON.stringify(Buffer.from(credentialResponse.response.clientDataJSON))
        })
    },
    register_certificate: (userId:string)=>{
        return instance.post("/register_certificate",{
            id:userId
        })
    },
    login: (username: string, password: string) => {
        let localStorageRepository = new LocalStorageRepository();
        return instance.post("/login", {
            username: username,
            password: password
        }).then((response: any) => {
            localStorageRepository.saveUser(response.data)
        })
    },
    login_fido: () => {
        return instance.post("/login_start")
            .then((response: any) => {
                CredentialService.getCredentials(response.data)
                    .then((assertionResponse: any) => {
                        AuthService.authenticate(assertionResponse)
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
    authenticate: (resp: any) => {
        let localStorageRepo = new LocalStorageRepository();
        return instance.post("/login_finish", {
            id: JSON.stringify(Buffer.from(resp.rawId)),
            signature: JSON.stringify(Buffer.from(resp.response.signature)),
            authData: JSON.stringify(Buffer.from(resp.response.authenticatorData)),
            userHandle: JSON.stringify(Buffer.from(resp.response.userHandle)),
            clientDataJSON: JSON.stringify(Buffer.from(resp.response.clientDataJSON)),
            type: resp.type
        }).then((response: any) => {
            console.log(response.data, '!!! sss')
            localStorageRepo.saveUser(response.data)
        })
    },
    logout: () => {
        let localStorageRepo = new LocalStorageRepository()
        localStorageRepo.removeUser()
    },
    getCurrentUser: () => {
        return instance.get("/",)
    },
    findById: (id: string) => {
        return instance.get(`/${id}`)
    }

}
export default AuthService;
