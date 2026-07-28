import Sidebar from "./Sidebar";
import Footer from "./Footer";

function Layout({ children }) {

    return (

        <>
            <Sidebar />

            <div
                style={{
                    marginLeft: "240px",
                    minHeight: "100vh",
                    display: "flex",
                    flexDirection: "column"
                }}
            >

                <div
                    style={{
                        flex: 1,
                        padding: "20px"
                    }}
                >
                    {children}
                </div>

                <Footer />

            </div>

        </>

    );

}

export default Layout;