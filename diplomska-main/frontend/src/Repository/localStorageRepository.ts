export default class LocalStorageRepository {


    save(id: string, value: any): void {
        localStorage.setItem(id, value);
    }

    delete(id: string): void {
        localStorage.removeItem(id);
    }

    findById(id: string) {
        return localStorage.getItem(id)
    }

    clear(): void {
        localStorage.clear();
    }

    saveUser(user: any): void {
        localStorage.setItem("user", JSON.stringify(user))
    }

    getUser() {
        return localStorage.getItem("user")
    }

    removeUser() {
        return localStorage.removeItem("user")
    }

    saveType(type: string) {
        localStorage.setItem("type", type);
    }

    getType() {
        return localStorage.getItem("type");
    }
}

