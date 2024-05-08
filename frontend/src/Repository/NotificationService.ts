import {toast} from "react-toastify";

const NotificationService = {
    success: (message: string) => {
        return toast.success(
            message, {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: false,
                progress: undefined
            }
        )
    },
    error: (message: string) => {
        return toast.error(
            message, {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: false,
                progress: undefined
            }
        )
    }
}
export default NotificationService;
