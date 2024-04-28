import React from 'react';
import ReactDOM from 'react-dom';
import "bootstrap/dist/css/bootstrap.css";
import './index.css';
import reportWebVitals from './reportWebVitals';
import "react-toastify/dist/ReactToastify.css";
import App from "./Components/App/App";
//@ts-ignore
import TimeAgo from 'javascript-time-ago';
//@ts-ignore
import en from 'javascript-time-ago/locale/en';

TimeAgo.addDefaultLocale(en)
ReactDOM.render(
    <React.StrictMode>
           <App/>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
