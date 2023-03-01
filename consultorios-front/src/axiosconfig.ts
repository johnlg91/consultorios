import axios from "axios";

//URL base para conectarse al back-end
const URL = axios.create({
	baseURL: "http://localhost:8080/",
});

// Add a response interceptor
URL.interceptors.response.use(function(response) {
	// a returned html page is the login page, redirect to the login then
	const contentType = response.headers["content-type"] || "";
	if (contentType.indexOf("text/html") > -1) {
		location.href = "login";
		throw new Error("login-required");
	}
	return response;
});

export default URL;