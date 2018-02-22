import React, { Component } from 'react';
import { connect } from 'react-redux';
import cx from 'classnames';
import TextField from "../components/TextField";
import '../../css/views/ApiConnect.scss';

class ShopifyApiConnect extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: props.shopName
        };

        this.handleTextFieldChange = this.handleTextFieldChange.bind(this);
    }

    handleTextFieldChange(event) {
        const target = event.target;

        this.setState({
            value: target.value
        });
    }

    render() {
        const { value } = this.state;
        const { shopName, userId } = this.props;
        let connected = !!shopName;

        return (
            <div className={cx('ApiConnect', {'ApiConnect-connected': connected})}>
                <div className="ApiConnect--name">Shopify</div>
                {
                    connected
                        ?
                        <div>Connected as {shopName}</div>
                        :
                        <div>
                            <TextField label="Enter your shop name" value={value} onChange={this.handleTextFieldChange} />
                            <a href={`service/shopify/oauth/authorize?userId=${userId}&shopName=${value}`}>
                                Connect
                            </a>
                        </div>
                }
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    shopName: state.user.shopName || '',
    userId: state.user.id,
});

const mapDispatchToProps = (dispatch) => ({

});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ShopifyApiConnect);