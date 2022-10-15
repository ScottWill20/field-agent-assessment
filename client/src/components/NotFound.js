import { Link } from "react-router-dom";

function NotFound() {
    return (
    <div className="container">
        <h1>Not Found</h1>
        <div>
            <img src="https://media.giphy.com/media/yF0YkUfXAZxtDkAALp/giphy.gif" 
            alt="404 Not found gif" 
            />
        </div>
        <p>
            Click <Link to="/">here</Link> to go back home.
        </p>
    </div>
    );
}

export default NotFound;