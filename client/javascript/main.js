const endpoint = 'http://localhost:8080/api/agent';
let agents = [];
let editAgentId = 0;

function displayList(){
    setCurrentView('List');
    getAgents()
    .then(data=>{agents = data;
        renderList(data)
    });
}

async function getAgents(){
    const response = await fetch(endpoint);
    return await response.json();
}

function setCurrentView(view){
    const formContainerElement = document.getElementById('formContainer');
    const listContainerElement = document.getElementById('listContainer');

    switch (view) {
        case 'List':
            formContainerElement.style.display = 'none';
            listContainerElement.style.display = 'block';
            break;
        case 'Form':
            formContainerElement.style.display = 'block';
            listContainerElement.style.display = 'none';
    }
}

function renderList(agents) {
    const agentsHTML = agents.map(ag => {
        return `
        <tr>
            <td>${ag.firstName}</td>
            <td>${ag.middleName}</td>
            <td>${ag.lastName}</td>
            <td>${ag.dob}</td>
            <td>${ag.heightInInches}</td>
            <td class="btnColumn">
                <button class="btn btn-info" onclick="handleEditAgent(${ag.agentId})">Edit</button>
                <button class="btn btn-danger" onclick="handleDeleteAgent(${ag.agentId})">Delete</button>
            </td>
        </tr>
        `;
    });
    const tableBodyElement = document.getElementById('tableRows');
    tableBodyElement.innerHTML = agentsHTML.join('');
}

function handleSubmit(event) {
    event.preventDefault();
    const firstName = document.getElementById('firstName').value;
    const middleName = document.getElementById('middleName').value;
    const lastName = document.getElementById('lastName').value;
    const dob = document.getElementById('dob').value;
    const heightInInches = document.getElementById('heightInInches').value;

    const agent = {
        firstName,
        middleName,
        lastName,
        dob,
        heightInInches: heightInInches ? parseInt(heightInInches) : 0
    };

    if(editAgentId > 0) {
        doPut(agent);
    } else {
        doPost(agent);
    }
}

function handleAddAgent() {
    setCurrentView('Form');
}

function handleEditAgent(agentId) {
    const agent = agents.find(agent => agent.agentId === agentId);

    document.getElementById('firstName').value = agent.firstName;
    document.getElementById('middleName').value = agent.middleName;
    document.getElementById('lastName').value = agent.lastName;
    document.getElementById('dob').value = agent.dob;
    document.getElementById('heightInInches').value = agent.heightInInches;

    document.getElementById('formHeading').innerText = "Update Agent";
    document.getElementById('formSubmitButton').innerText = "Update Agent";

    editAgentId = agentId;
    setCurrentView('Form');

}

function handleDeleteAgent(agentId) {
    const agent = agents.find(agent => agent.agentId === agentId);
    if (confirm(`Delete Agent ${agent.firstName} ${agent.middleName} ${agent.lastName} ?`)) {
        const init = {
            method: 'DELETE'
        };

        fetch(`${endpoint}/${agentId}`, init)
        .then(response => {
            if (response.status === 204) {
                displayList();
                resetState();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .catch(console.log);
    }
}

function renderErrors(errors){
    const errorsHTML = errors.map(error => `<li>${error}</li>`);
    const errorsHTMLString = `
        <p>The following errors were found:</p>
        <ul>
            ${errorsHTML.join('')}
        </ul>
    `;

    document.getElementById(`errors`).innerHTML = errorsHTMLString;
}

function doPost(agent){
    const init = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(agent)
    };
    fetch(`${endpoint}`, init)
    .then(response =>{
        if (response.status === 201 || response.status == 400) {
            return response.json();
        } else {
            return Promise.reject(`Unexpected status code: ${response.status}`);
        }
    })
    .then(data =>{
        if (data.agentId) {
            displayList();
            resetState();
        } else {
            renderErrors(data);
        }
    })
    .catch(error => console.log(error));
}

function doPut(agent){
    agent.agentId = editAgentId;

    const init = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(agent)
    };

    fetch(`${endpoint}/${editAgentId}`, init)
    .then(response => {
        if (response.status === 204) {
            return null;
        } else if (response.status === 400) {
            return response.json();
        } else {
            return Promise.reject(`Unexpected status code: ${response.status}`);
        }
    })
    .then(data => {
        if (!data) {
            displayList();
            resetState();
        } else {
            renderErrors(data);
        }
    })
    .catch(console.log);
}

function resetState() {
    document.getElementById('form').reset();
    document.getElementById('formSubmitButton').innerText = 'Add Agent';
    document.getElementById('errors').innerHTML = '';
    editAgentId = 0;
    setCurrentView('List');
}

displayList();