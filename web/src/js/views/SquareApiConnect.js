import React, { Component } from 'react';
import { connect } from 'react-redux';
import cx from 'classnames';
import '../../css/views/ApiConnect.scss';

const SquareApiConnect = ({merchantId, userId}) => {
    let connected = !!merchantId;
    return (
        <div className={cx("ApiConnect", {'ApiConnect-connected': connected})} >
            <div className='ApiConnect--name'>Square</div>
            {
                connected
                    ?
                        <div>Connected as {merchantId}</div>
                    :
                        <a href={`service/square/oauth/authorize?userId=${userId}`}>Connect</a>
            }
        </div>
    );
};


const mapStateToProps = (state) => ({
    merchantId: state.user.merchantId,
    userId: state.user.id,
});

const mapDispatchToProps = (dispatch) => ({

});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SquareApiConnect);