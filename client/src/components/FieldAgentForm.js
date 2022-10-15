import { useEffect, useState } from "react";
import { useHistory, Link, useParams } from "react-router-dom";

const FIELD_AGENT_DEFAULT = {
    firstName: "",
    middleName: "",
    lastName: "",
    dob: "1900-01-01",
    heightInInches: 0
};

function FieldAgentForm() {
    const endpoint = "http://localhost:8080/api/agent";
    const [agent, setAgent] = useState(FIELD_AGENT_DEFAULT);
    const [editAgentId, setEditAgentId] = useState(0);
    const [errors, setErrors] = useState([]);
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        if(id) {
            setEditAgentId(id);
        fetch(`${endpoint}/${id}`)
        .then((response) => response.json())
        .then((data) => setAgent(data));
        }
    }, [id]);

    const handleChange = (event) => {
        const newAgent = {...agent};
        newAgent[event.target.name] = event.target.value;
        setAgent(newAgent);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (editAgentId === 0) {
            addAgent();
        } else {
            updateAgent();
        }
    };

    const updateAgent = () => {
        agent.id = editAgentId;
        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(agent),
        };

        fetch(`${endpoint}/${editAgentId}`, init)
        .then(response => {
            if (response.status === 204) {
                return null;
            } else if (response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status ${response.status}`); 
            }
        })
        .then(data => {
            if (!data) {
                resetState();
                history.push("/");
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    };

    const addAgent = () => {
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(agent),
        };
        
        fetch(endpoint, init)
        .then(response => {
            if (response.status === 201 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status ${response.status}`); 
            }
        })
        .then(data => {
            if(data.id) {
                resetState();
                history.push("/");
            } else {
                setErrors(data);
            }
        })
        .catch(error => console.log(error))
    };

    const resetState = () => {
        setAgent(FIELD_AGENT_DEFAULT);
        setEditAgentId(0);
        setErrors([]);    
    };

    return (
    <>
        <h2>{editAgentId > 0 ? "Update Agent" : "Add Agent"}</h2>

        {errors.length > 0 && (
                <div>
                    <h3>The following errors occured:</h3>
                    <ul>
                        {errors.map((error) => {
                            return <li>{error}</li>;
                        })}
                    </ul>
                </div>
            )}

        <form onSubmit={handleSubmit}>
            <div className="form-group">
                <label htmlFor="firstName">First:</label>
                <input id="firstName" 
                name="firstName" 
                type="text" 
                className="form-control"
                value={agent.firstName}
                onChange={handleChange}   
                required 
                />
            </div>
            <div className="form-group">
                <label htmlFor="middleName">Middle:</label>
                <input id="middleName" 
                name="middleName" 
                type="text" 
                className="form-control"
                value={agent.middleName}
                onChange={handleChange}   
                />
            </div>
            <div className="form-group">
                <label htmlFor="lastName">Last:</label>
                <input id="lastName" 
                name="lastName" 
                type="text" 
                className="form-control"
                value={agent.lastName}
                onChange={handleChange}   
                required 
                />
            </div>
            <div className="form-group">
                <label htmlFor="dob">Date of Birth:</label>
                <input id="dob" 
                name="dob" 
                type="date" 
                className="form-control"
                value={agent.dob}
                onChange={handleChange}   
                />
            </div>
            <div className="form-group">
                <label htmlFor="heightInInches">Height (Inches):</label>
                <input id="heightInInches" 
                name="heightInInches" 
                type="number" 
                className="form-control"
                value={agent.heightInInches}
                onChange={handleChange}   
                required 
                />
            </div>
            <div className="mt-4">
                <button className="btn btn-success mr-4"
                type="submit">
                    {editAgentId > 0 ? 'Update Agent' : 'Add Agent'}
                </button>
                <Link className="btn btn-warning" to="/">Cancel</Link>
            </div>
        </form>
         </>
)
}

export default FieldAgentForm;
