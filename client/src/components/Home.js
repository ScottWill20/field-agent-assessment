
import { Link } from "react-router-dom";
function Home() {
    return (
        <>
        <h2>Welcome!</h2>

        <ul>
            <li>
                <Link to="/agents">View All Agents</Link>
            </li>
        </ul>
        </>
    );

}

export default Home;