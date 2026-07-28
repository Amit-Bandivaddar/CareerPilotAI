import { useState } from "react";
import { toast } from "react-toastify";
import ReactMarkdown from "react-markdown";
import { CopyToClipboard } from "react-copy-to-clipboard";
import jsPDF from "jspdf";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function SkillGap() {

    const [role, setRole] = useState("");
    const [result, setResult] = useState("");
    const [loading, setLoading] = useState(false);

    const downloadPDF = () => {

        const pdf = new jsPDF();

        pdf.setFontSize(18);
        pdf.text("Skill Gap Analysis", 10, 20);

        pdf.setFontSize(12);

        const lines = pdf.splitTextToSize(result, 180);

        pdf.text(lines, 10, 35);

        pdf.save("Skill_Gap_Analysis.pdf");

        toast.success("PDF Downloaded Successfully!");

    };

    const analyze = async () => {

        if (!role.trim()) {

            toast.warning("Please enter a target role.");
            return;

        }

        try {

            setLoading(true);

            const userId = localStorage.getItem("userId");

            const response = await api.get(
                `/skill-gap/${userId}?role=${role}`
            );

            setResult(response.data);

        } catch (error) {

            toast.error("Failed to analyze Skill Gap.");

            setResult("Failed to analyze Skill Gap.");

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

                <h1>Skill Gap Analysis</h1>

                <input
                    type="text"
                    placeholder="Enter Target Role"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    style={{
                        padding: "10px",
                        width: "300px",
                        marginRight: "10px",
                        borderRadius: "8px",
                        border: "1px solid #ccc"
                    }}
                />

                <button
                    onClick={analyze}
                    style={{
                        padding: "10px 20px",
                        background: "#2563eb",
                        color: "white",
                        border: "none",
                        borderRadius: "8px",
                        cursor: "pointer"
                    }}
                >
                    Analyze
                </button>

                {result && (

                    <>

                        <div
                            style={{
                                display: "flex",
                                gap: "10px",
                                margin: "20px 0"
                            }}
                        >

                            <CopyToClipboard
                                text={result}
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
                                boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                                marginTop: "20px"
                            }}
                        >

                            <ReactMarkdown>

                                {result}

                            </ReactMarkdown>

                        </div>

                    </>

                )}

            </div>

        </Layout>

    );

}

export default SkillGap;