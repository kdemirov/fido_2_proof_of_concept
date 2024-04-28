import axios from "axios";
import LocalStorageRepository from "../Repository/localStorageRepository";


const instance = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
});
instance.interceptors.request.use(
    config => {
        let localStorageRepo = new LocalStorageRepository()
        const token = localStorageRepo.getUser();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            console.log("Error setting header")
        }
        return config;

    }
)
instance.interceptors.response.use(
    (response: any) => {
        let localStorageRepo = new LocalStorageRepository()
        if (response.headers.Authorization) {
            localStorageRepo.saveUser(response.headers.Authorization)
        }
        return response
    }, (error: any) => {
        let localStorageRepo = new LocalStorageRepository()
        localStorageRepo.removeUser()
        window.location.href = "/login"
        return Promise.reject(error)
    }
)
export default instance;
