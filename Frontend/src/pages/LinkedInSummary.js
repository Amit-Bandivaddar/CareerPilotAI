import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import ReactMarkdown from "react-markdown";
import { CopyToClipboard } from "react-copy-to-clipboard";
import jsPDF from "jspdf";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function LinkedInSummary() {

    const [summary, setSummary] = useState("");
    const [loading, setLoading] = useState(true);

    const downloadPDF = () => {

        const pdf = new jsPDF();

        pdf.setFontSize(18);
        pdf.text("LinkedIn Summary", 10, 20);

        pdf.setFontSize(12);

        const lines = pdf.splitTextToSize(summary, 180);

        pdf.text(lines, 10, 35);

        pdf.save("LinkedIn_Summary.pdf");

        toast.success("PDF Downloaded Successfully!");

    };

    useEffect(() => {

        const loadSummary = async () => {

            try {

                setLoading(true);

                const userId = localStorage.getItem("userId");

                const response = await api.get(`/linkedin-summary/${userId}`);

                setSummary(response.data);

            } catch (error) {

                toast.error("Failed to generate LinkedIn Summary.");

                setSummary("Failed to generate LinkedIn Summary.");

            } finally {

                setLoading(false);

            }

        };

        loadSummary();

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

                <h1>LinkedIn Summary</h1>

                <div
                    style={{
                        display: "flex",
                        gap: "10px",
                        margin: "20px 0"
                    }}
                >

                    <CopyToClipboard
                        text={summary}
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

                        {summary}

                    </ReactMarkdown>

                </div>

            </div>

        </Layout>

    );

}

export default LinkedInSummary;