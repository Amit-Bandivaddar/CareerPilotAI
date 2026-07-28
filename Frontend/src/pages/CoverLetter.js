import { useState } from "react";
import { toast } from "react-toastify";
import ReactMarkdown from "react-markdown";
import { CopyToClipboard } from "react-copy-to-clipboard";
import jsPDF from "jspdf";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function CoverLetter() {

    const [company, setCompany] = useState("");
    const [role, setRole] = useState("");
    const [coverLetter, setCoverLetter] = useState("");
    const [loading, setLoading] = useState(false);

    const downloadPDF = () => {

        const pdf = new jsPDF();

        pdf.setFontSize(18);
        pdf.text("Cover Letter", 10, 20);

        pdf.setFontSize(12);

        const lines = pdf.splitTextToSize(coverLetter, 180);

        pdf.text(lines, 10, 35);

        pdf.save("Cover_Letter.pdf");

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
                `/cover-letter/${userId}?company=${company}&role=${role}`
            );

            setCoverLetter(response.data);

        } catch (error) {

            toast.error("Failed to generate Cover Letter.");

            setCoverLetter("Failed to generate Cover Letter.");

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

                <h1>Cover Letter</h1>

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
                    Generate Cover Letter
                </button>

                {coverLetter && (

                    <>

                        <div
                            style={{
                                display: "flex",
                                gap: "10px",
                                margin: "20px 0"
                            }}
                        >

                            <CopyToClipboard
                                text={coverLetter}
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

                                {coverLetter}

                            </ReactMarkdown>

                        </div>

                    </>

                )}

            </div>

        </Layout>

    );

}

export default CoverLetter;