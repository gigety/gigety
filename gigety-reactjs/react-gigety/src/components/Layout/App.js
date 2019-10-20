import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Root from 'components/Layout/Root';
import Header from './Header';
import Landing from './Landing';
class App extends Component {
	render() {
		return (
			<div className="ui container">
				<Root>
					<BrowserRouter>
						<div>
							<Header />
							<Route exact path="/" component={Landing} />
							<Switch></Switch>
						</div>
					</BrowserRouter>
				</Root>
			</div>
		);
	}
}
export default App;
