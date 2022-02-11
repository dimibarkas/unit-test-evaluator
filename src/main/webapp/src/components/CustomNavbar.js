import {Container, Navbar} from "react-bootstrap";

function CustomNavbar() {
    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                    <svg width={20} height={20}>
                        <polygon points={"10,20  10,0 20,10"}
                                 style={{fill: "grey"}}/>
                    </svg>
                    {' '}
                    <div style={{paddingLeft: ".4rem"}}>
                        Unit Test Evaluator
                    </div>
                </Navbar.Brand>
            </Container>
        </Navbar>
    )
}

export default CustomNavbar;