import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import ReactMarkdown from "react-markdown";
import { CopyToClipboard } from "react-copy-to-clipboard";
import jsPDF from "jspdf";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function ResumeAnalysis() {

    const [analysis, setAnalysis] = useState("");
    const [loading, setLoading] = useState(true);

    const downloadPDF = () => {

        const pdf = new jsPDF();

        pdf.setFontSize(18);
        pdf.text("Resume Analysis", 10, 20);

        pdf.setFontSize(12);

        const lines = pdf.splitTextToSize(analysis, 180);

        pdf.text(lines, 10, 35);

        pdf.save("Resume_Analysis.pdf");

        toast.success("PDF Downloaded Successfully!");

    };

    useEffect(() => {

        const fetchAnalysis = async () => {

            try {

                setLoading(true);

                const userId = localStorage.getItem("userId");

                const response = await api.get(`/analyze-resume/${userId}`);

                setAnalysis(response.data);

            } catch (error) {

                toast.error("Failed to analyze resume.");

                setAnalysis("Failed to analyze resume.");

            } finally {

                setLoading(false);

            }

        };

        fetchAnalysis();

    }, []);

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

                <h1>Resume Analysis</h1>

                <div
                    style={{
                        display: "flex",
                        gap: "10px",
                        margin: "20px 0"
                    }}
                >

                    <CopyToClipboard
                        text={analysis}
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

                        {analysis}

                    </ReactMarkdown>

                </div>

            </div>

        </Layout>

    );

}

export default ResumeAnalysis;