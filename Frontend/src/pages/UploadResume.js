import { useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import { toast } from "react-toastify";

function UploadResume() {

    const [file, setFile] = useState(null);

    const uploadResume = async () => {

        if (!file) {

           toast.warning("Please select a resume.");

            return;
        }

        const formData = new FormData();

        formData.append("file", file);

        try {

            const userId = localStorage.getItem("userId");

await api.post(`/upload/${userId}`, formData, {
    headers: {
        "Content-Type": "multipart/form-data",
    },
});

            toast.success("Resume Uploaded Successfully!");

        } catch (error) {

            toast.error("Upload Failed!");

        }

    };

    return (

    <Layout>

        <div style={{ textAlign: "center" }}>

            <h2>Upload Resume</h2>

            <input
                type="file"
                accept=".pdf"
                onChange={(e) => setFile(e.target.files[0])}
            />

            <br /><br />

            <button onClick={uploadResume}>
                Upload Resume
            </button>

        </div>

    </Layout>

);

}

export default UploadResume;