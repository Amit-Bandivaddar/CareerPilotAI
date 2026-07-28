import { useState } from "react";
import { toast } from "react-toastify";
import ReactMarkdown from "react-markdown";
import { CopyToClipboard } from "react-copy-to-clipboard";
import jsPDF from "jspdf";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function HREmail() {

    const [company, setCompany] = useState("");
    const [role, setRole] = useState("");
    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);

    const downloadPDF = () => {

        const pdf = new jsPDF();

        pdf.setFontSize(18);
        pdf.text("HR Email", 10, 20);

        pdf.setFontSize(12);

        const lines = pdf.splitTextToSize(email, 180);

        pdf.text(lines, 10, 35);

        pdf.save("HR_Email.pdf");

        toast.success("PDF Downloaded Successfully!");

    };

    const generate = async () => {

        if (!company.trim()) {

            toast.warning("Please enter company name.");
            return;

        }

        if (!role.trim()) {

            toast.warning("Please enter role.");
            return;

        }

        try {

            setLoading(true);

            const userId = localStorage.getItem("userId");

            const response = await api.get(
                `/hr-email/${userId}?company=${company}&role=${role}`
            );

            setEmail(response.data);

        } catch (error) {

            toast.error("Failed to generate HR Email.");

            setEmail("Failed to generate HR Email.");

        } finally {

            setLoading(false);

        }

    };

    if (loading) {

        return (

            <Layout>

                <Loading />

            </Layout>

        );

    }

    return (

        <Layout>

            <div style={{ padding: "40px" }}>

                <h1>HR Email Generator</h1>

                <input
                    type="text"
                    placeholder="Company Name"
                    value={company}
                    onChange={(e) => setCompany(e.target.value)}
                    style={{
                        width: "350px",
                        padding: "10px",
                        borderRadius: "8px",
                        border: "1px solid #ccc"
                    }}
                />

                <br /><br />

                <input
                    type="text"
                    placeholder="Job Role"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    style={{
                        width: "350px",
                        padding: "10px",
                        borderRadius: "8px",
                        border: "1px solid #ccc"
                    }}
                />

                <br /><br />

                <button
                    onClick={generate}
                    style={{
                        padding: "10px 20px",
                        background: "#2563eb",
                        color: "white",
                        border: "none",
                        borderRadius: "8px",
                        cursor: "pointer"
                    }}
                >
                    Generate HR Email
                </button>

                {email && (

                    <>

                        <div
                            style={{
                                display: "flex",
                                gap: "10px",
                                margin: "20px 0"
                            }}
                        >

                            <CopyToClipboard
                                text={email}
                                onCopy={() => toast.success("Copied Successfully!")}
                            >

                                <button
                                    style={{
                                        padding: "10px 20px",
                                        background: "#16a34a",
                                        color: "white",
                                        border: "none",
                                        borderRadius: "8px",
                                        cursor: "pointer"
                                    }}
                                >
                                    📋 Copy
                                </button>

                            </CopyToClipboard>

                            <button
                                onClick={downloadPDF}
                                style={{
                                    padding: "10px 20px",
                                    background: "#2563eb",
                                    color: "white",
                                    border: "none",
                                    borderRadius: "8px",
                                    cursor: "pointer"
                                }}
                            >
                                📄 Download PDF
                            </button>

                        </div>

                        <div
                            style={{
                                background: "#ffffff",
                                padding: "25px",
                                borderRadius: "12px",
                                boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
                            }}
                        >

                            <ReactMarkdown>

                                {email}

                            </ReactMarkdown>

                        </div>

                    </>

                )}

            </div>

        </Layout>

    );

}

export default HREmail;