import { useState } from "react";
import { toast } from "react-toastify";
import ReactMarkdown from "react-markdown";
import { CopyToClipboard } from "react-copy-to-clipboard";
import jsPDF from "jspdf";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function CareerRoadmap() {

    const [goal, setGoal] = useState("");
    const [roadmap, setRoadmap] = useState("");
    const [loading, setLoading] = useState(false);

    const downloadPDF = () => {

        const pdf = new jsPDF();

        pdf.setFontSize(18);
        pdf.text("Career Roadmap", 10, 20);

        pdf.setFontSize(12);

        const lines = pdf.splitTextToSize(roadmap, 180);

        pdf.text(lines, 10, 35);

        pdf.save("Career_Roadmap.pdf");

        toast.success("PDF Downloaded Successfully!");

    };

    const generateRoadmap = async () => {

        if (!goal.trim()) {

            toast.warning("Please enter your career goal.");
            return;

        }

        try {

            setLoading(true);

            const userId = localStorage.getItem("userId");

            const response = await api.get(
                `/career-roadmap/${userId}?goal=${goal}`
            );

            setRoadmap(response.data);

        } catch (error) {

            toast.error("Failed to generate Career Roadmap.");

            setRoadmap("Failed to generate Career Roadmap.");

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

                <h1>Career Roadmap</h1>

                <input
                    type="text"
                    placeholder="Enter Career Goal"
                    value={goal}
                    onChange={(e) => setGoal(e.target.value)}
                    style={{
                        width: "350px",
                        padding: "10px",
                        marginRight: "10px",
                        borderRadius: "8px",
                        border: "1px solid #ccc"
                    }}
                />

                <button
                    onClick={generateRoadmap}
                    style={{
                        padding: "10px 20px",
                        background: "#2563eb",
                        color: "white",
                        border: "none",
                        borderRadius: "8px",
                        cursor: "pointer"
                    }}
                >
                    Generate Roadmap
                </button>

                {roadmap && (

                    <>

                        <div
                            style={{
                                display: "flex",
                                gap: "10px",
                                margin: "20px 0"
                            }}
                        >

                            <CopyToClipboard
                                text={roadmap}
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

                                {roadmap}

                            </ReactMarkdown>

                        </div>

                    </>

                )}

            </div>

        </Layout>

    );

}

export default CareerRoadmap;