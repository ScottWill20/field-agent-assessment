import { Link } from "react-router-dom";

function NotFound() {
    return (
    <div>
        <h1>Not Found</h1>
        <p>
            Click <Link to="/">here</Link> to go back home.
        </p>
        <div>
            <img src="https://media.giphy.com/media/14uQ3cOFteDaU/giphy.gif" 
            alt="404 Not found gif" 
            />
        </div>
    </div>
    );
}

export default NotFound;