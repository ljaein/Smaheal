import axios from "axios";

export default axios.create({
    //baseURL: "http://192.168.28.129:8397/api/auth",
    // baseURL: "https://localhost:8080/api/auth",
    baseURL: "https://j3b.p.ssafy.io:8080/api/auth",
    headers: {
        "Content-type": "application/json"
    }
});