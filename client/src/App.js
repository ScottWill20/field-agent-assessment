import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Home from "./components/Home";
import FieldAgentList from "./components/FieldAgentList";
import FieldAgentForm from "./components/FieldAgentForm";
import NotFound from "./components/NotFound";

function App () {
  return(
    <Router>
        <h1>Field Agents</h1>
        <Switch>
          <Route path="/" exact>
            <Home />
          </Route>
          <Route path="/agents" exact>
            <FieldAgentList />
          </Route>
          <Route path={["/agents/add", "/agents/edit/:agentId"]}>
            <FieldAgentForm />
          </Route>
          <Route path="*">
            <NotFound />
          </Route>
        </Switch>
    </Router>
  );
}
export default App;
