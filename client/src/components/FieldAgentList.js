import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function FieldAgentList() {
    const endpoint = "http://localhost:8080/api/agent";
    const [agents, setAgents] = useState([]);

    useEffect(() => {
        getFieldAgents();
    }, []);

    const getFieldAgents =() => {
        fetch(endpoint).then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code ${response.status}`);
            }
        }).then(data => {
            setAgents(data);
        }).catch(console.error);
    };

    const handleDeletePanel = (agentId) => {
        const agent = agents.find(agent => agent.agentId === agentId);

        if(window.confirm(`Delete Agent ${agent.firstName} ${agent.lastName} ?`)) {
            const init = {
                method: "DELETE",
            };
            fetch(`${endpoint}/${agentId}`, init)
            .then(response => {
                    if (response.status === 204) {
                        getFieldAgents();
                    } else {
                        return Promise.reject(`Unexpected status code ${response.status}`);
                 }
            })
            .catch(console.log);
        }
    };

    return (
        <>
        <h2>Field Agents</h2>

        <Link className="btn btn-primary" to="/agents/add">
            Add Field Agent
        </Link>
        <table>
            <thead>
                <tr>
                    <th>First</th>
                    <th>Middle</th>
                    <th>Last</th>
                    <th>Date of Birth</th>
                    <th>Height (Inches)</th>
                    <th>&nbsp;</th>
                </tr>
            </thead>
            <tbody>
                {agents.map((agent) => (
                    <tr key={agent.agentId}>
                        <td>{agent.firstName}</td>
                        <td>{agent.middleName}</td>
                        <td>{agent.lastName}</td>
                        <td>{agent.dob}</td>
                        <td>{agent.heightInInches}</td>
                        <td className="buttonContainer">
                            <Link className="btn btn-primary" 
                            to={`agents/edit/${agent.agentId}`}>
                                Edit
                            </Link>
                            <button className="btn btn-danger"
                            onClick={() => handleDeletePanel(agent.agentId)}>
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
        </>
    );
}

export default FieldAgentList;