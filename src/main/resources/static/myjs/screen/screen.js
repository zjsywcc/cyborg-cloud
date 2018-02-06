$(document).ready(function () {


    var ajaxInterval = 1000;
    /**
     * 使用echart绘制象形柱状图
     * @param domName
     * @param fatigueValue
     */
    function drawChart(domName, fatigueValue) {
        // 基于准备好的dom，初始化echarts图表
        var path = 'path://M109.133,218.403c-37.51,0-68.027-30.518-68.027-68.028v-0.987h-1.488c-11.568,0-20.979-9.411-20.979-20.979v-9.605	c0-11.568,9.411-20.979,20.979-20.979h1.488V66.905c0-11.856,5.489-23.127,14.763-30.473c-0.312-1.557-0.474-3.157-0.474-4.783	c0-4.319,2.3-8.757,6.153-11.868c0.039-0.03,0.078-0.061,0.118-0.089L64,18.026c0.083-0.06,0.169-0.113,0.258-0.162	c0.364-0.198,0.775-0.445,1.247-0.729c2.198-1.318,5.52-3.312,11.011-4.988c7.609-2.491,15.597-3.943,24.421-4.438	c11.433-0.644,23.005,0.097,33.459,2.136c6.811,1.164,12.479,1.498,16.793,0.991c6.301-0.745,8.784-3.749,9.396-4.651	c0.726-1.072,0.878-3.054,0.874-3.663c-0.007-1.128,0.742-2.126,1.828-2.429c1.088-0.302,2.241,0.154,2.819,1.123	c0.136,0.228,3.306,5.652,1.673,12.248c-0.434,1.752-0.977,3.224-1.541,4.441c1.467-0.197,3.01-0.49,4.251-0.923	c3.643-1.269,4.929-1.842,4.941-1.848c0.918-0.411,1.996-0.234,2.73,0.45c0.736,0.686,0.992,1.744,0.646,2.688	c-0.251,0.685-2.578,6.771-7.007,9.657c-0.056,0.036-0.112,0.072-0.169,0.108c1.038,1.238,1.936,2.508,2.683,3.797	c1.147,1.772,1.878,3.291,2.782,5.237l0,0c7.704,16.569,1.282,33.971,0.064,36.966v23.784h1.624	c11.568,0,20.979,9.411,20.979,20.979v9.605c0,11.568-9.411,20.979-20.979,20.979h-1.624v0.987	C177.16,187.885,146.644,218.403,109.133,218.403z M95.701,211.962c4.329,0.943,8.824,1.44,13.432,1.44	c3.608,0,7.146-0.305,10.591-0.89c-0.015-0.109-0.022-0.221-0.022-0.334v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v3.352	c5.701-1.454,11.094-3.688,16.058-6.579c-1.399,0.078-2.635-1.07-2.635-2.496v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5	v4.073c0,0.463-0.126,0.897-0.346,1.269c8.669-5.494,15.913-13.046,21.038-21.961c-0.154-0.325-0.24-0.688-0.24-1.071v-4.073	c0-1.381,1.119-2.5,2.5-2.5c0.483,0,0.935,0.138,1.317,0.375c3.071-7.413,4.767-15.535,4.767-24.046v-21.19h-4.429	c-0.668,0-1.309-0.268-1.777-0.742c-0.47-0.475-0.73-1.118-0.723-1.786c0.004-0.29,0.303-29.082-1.926-37.996	c-2.013-8.056-7.763-11.271-8.006-11.404c-0.596-0.325-1.041-0.882-1.221-1.536c-0.18-0.655-0.092-1.358,0.254-1.941	c0.033-0.056,3.432-5.878,3.582-11.459c0.022-0.828-3.23-2.79-7.637-4.141c-5.721-1.298-10.214-1.221-10.314-1.22L87.9,56.959	c-7.45,0-13.899-1.269-19.167-3.771c-0.96-0.456-1.88-0.977-2.756-1.556c-2.334,2.704-5.634,7.289-5.625,10.748	c0.014,5.524,3.523,11.304,3.559,11.361c0.361,0.589,0.464,1.302,0.283,1.968c-0.182,0.666-0.63,1.229-1.24,1.553	c-0.231,0.126-5.979,3.341-7.993,11.397c-2.229,8.917-1.929,37.706-1.925,37.996c0.007,0.668-0.252,1.311-0.722,1.786	c-0.47,0.475-1.11,0.742-1.778,0.742h-4.429v21.19c0,7.988,1.494,15.636,4.217,22.675c0.456-0.609,1.184-1.004,2.003-1.004	c1.381,0,2.5,1.119,2.5,2.5v4.073c0,0.812-0.386,1.532-0.985,1.989c2.924,5.326,6.594,10.186,10.871,14.438	c0.114-1.274,1.186-2.273,2.49-2.273c1.381,0,2.5,1.119,2.5,2.5v4.073c0,0.055-0.002,0.108-0.005,0.161	c6.16,4.954,13.27,8.777,21.005,11.147v-1.548c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5V211.962z M177.297,144.387h1.487	c8.812,0,15.979-7.168,15.979-15.979v-9.605c0-8.812-7.168-15.979-15.979-15.979h-1.487V144.387z M39.618,102.823	c-8.811,0-15.979,7.168-15.979,15.979v9.605c0,8.812,7.168,15.979,15.979,15.979h1.488v-41.564H39.618z M170.247,124.184h1.913	V73.921c-2.185-4.729-5.547-8.139-9.29-10.601c-0.309,4.355-1.992,8.521-3.144,10.922c2.526,1.973,6.664,6.137,8.431,13.204	C170.155,95.443,170.271,116.786,170.247,124.184z M46.106,124.184h1.913c-0.023-7.398,0.092-28.741,2.091-36.737	c1.761-7.044,5.876-11.204,8.404-13.185c-1.272-2.577-3.15-7.172-3.162-11.87c-0.013-5.306,4.218-11.003,6.777-13.961	c-0.048-0.05-0.095-0.1-0.143-0.149c-1.858-1.932-3.386-4.189-4.504-6.678c-7.177,6.391-11.377,15.622-11.377,25.301V124.184z	 M149.856,52.869c7.654,1.488,17.906,5.192,24.248,14.377c0.354-1.344,0.706-2.903,0.997-4.627	c-2.934-2.858-13.531-12.211-29.298-15.209c-11.021-2.096-24.043-1.283-36.632-0.499c-9.361,0.583-19.041,1.187-27.616,0.536	c-8.314-0.632-14.078-2.802-18.02-5.228c0.53,0.809,1.121,1.573,1.767,2.288c0.49,0.534,1.011,1.037,1.561,1.508	c0.224,0.117,0.435,0.27,0.622,0.458c0.055,0.056,0.107,0.112,0.156,0.172c0.956,0.73,1.987,1.366,3.083,1.894	c4.612,2.221,10.38,3.347,17.145,3.347h51.971c0.523-0.016,3.953-0.049,8.595,0.727c0.206,0.001,0.413,0.016,0.622,0.063	C149.287,52.73,149.557,52.794,149.856,52.869z M129.062,41.077c6.125,0,12.095,0.361,17.675,1.422	c13.856,2.635,23.881,9.605,29.016,13.95c0.081-2.007,0.035-4.119-0.198-6.283c-1.151-0.97-11.101-9.229-19.929-12.866	c-9.171-3.777-23.86-2.613-24.009-2.604c-0.844,0.069-1.648-0.284-2.168-0.941c-0.52-0.658-0.675-1.531-0.414-2.327l0.616-1.883	l-14.492,4.546c-0.887,0.278-1.853,0.043-2.511-0.613c-0.658-0.654-0.899-1.62-0.627-2.507l1.533-4.985	c-7.726,5.057-21.352,12.59-32.577,11.372c-10.919-1.174-16.747-6.219-19.539-9.727c-0.672,1.316-1.043,2.698-1.043,4.019	c0,0.235,0.004,0.47,0.013,0.703c0.811,1.57,5.428,8.887,21.526,10.109c8.23,0.627,17.312,0.059,26.926-0.54	C115.574,41.502,122.409,41.077,129.062,41.077z M137.064,29.511c5.573,0,14.027,0.513,20.466,3.165	c5.95,2.451,12.162,6.67,16.352,9.799c-0.374-1.103-0.811-2.202-1.318-3.295c0,0,0,0-0.001,0c-0.853-1.834-1.485-3.15-2.477-4.675	c-0.024-0.038-0.049-0.077-0.071-0.116c-1.024-1.78-2.425-3.539-4.162-5.228c-0.594-0.576-0.863-1.409-0.721-2.225	c0.144-0.814,0.68-1.506,1.434-1.847c0.938-0.424,1.781-0.878,2.506-1.35c0.652-0.425,1.264-1.005,1.822-1.657	c-4.091,1.104-9.295,1.217-9.536,1.222c-0.017,0-0.032,0-0.048,0c-1.035,0-1.966-0.639-2.336-1.608	c-0.376-0.985-0.093-2.101,0.706-2.788l0,0c-0.001,0.001,2.085-1.953,3.246-6.646c0.121-0.487,0.203-0.968,0.254-1.438	c-1.933,1.904-5.459,4.273-11.403,4.976c-4.789,0.566-10.938,0.217-18.279-1.038c-10.11-1.971-21.251-2.678-32.279-2.062	c-8.396,0.472-15.978,1.848-23.177,4.209c-0.036,0.012-0.073,0.022-0.11,0.033c-4.86,1.484-7.865,3.287-9.854,4.48	c-0.481,0.288-0.908,0.544-1.291,0.756l-2.083,1.487c1.733,2.538,6.432,7.604,16.808,8.72c14.332,1.538,34.837-14.482,35.042-14.646	c0.861-0.677,2.061-0.714,2.961-0.099c0.902,0.619,1.298,1.752,0.977,2.798l-2.294,7.457l14.594-4.578	c0.893-0.28,1.868-0.037,2.526,0.629s0.889,1.645,0.598,2.534l-1,3.055C135.566,29.521,136.287,29.511,137.064,29.511z	 M109.701,208.882c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073	C112.201,207.763,111.082,208.882,109.701,208.882z M84.779,203.882c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5	s2.5,1.119,2.5,2.5v4.073C87.279,202.763,86.159,203.882,84.779,203.882z M101.701,194.882c-1.381,0-2.5-1.119-2.5-2.5v-4.073	c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C104.201,193.763,103.082,194.882,101.701,194.882z M121.201,192.845	c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073	C123.701,191.726,122.582,192.845,121.201,192.845z M148.9,191.192c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5	s2.5,1.119,2.5,2.5v4.073C151.4,190.073,150.28,191.192,148.9,191.192z M134.951,185.765c-1.381,0-2.5-1.119-2.5-2.5v-4.073	c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C137.451,184.646,136.332,185.765,134.951,185.765z M69.504,185.192	c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073	C72.004,184.073,70.884,185.192,69.504,185.192z M86.451,183.765c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5	s2.5,1.119,2.5,2.5v4.073C88.951,182.646,87.832,183.765,86.451,183.765z M109.133,183.119c-1.381,0-2.5-1.119-2.5-2.5v-4.073	c0-1.381,1.119-2.5,2.5-2.5c1.381,0,2.5,1.119,2.5,2.5v4.073C111.633,181.999,110.514,183.119,109.133,183.119z M124.08,179.083	c-0.908,0-1.783-0.497-2.226-1.359c-0.168-0.318-3.861-7.142-13.153-7.142c-9.424,0-13.111,7.059-13.147,7.13	c-0.625,1.233-2.129,1.724-3.36,1.1c-1.231-0.624-1.724-2.128-1.1-3.359c0.205-0.403,5.146-9.87,17.607-9.87	s17.402,9.467,17.606,9.87c0.625,1.231,0.133,2.735-1.1,3.359C124.847,178.995,124.46,179.083,124.08,179.083z M157.576,170.2	c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C160.076,169.08,158.957,170.2,157.576,170.2	z M143.451,169.236c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073	C145.951,168.117,144.832,169.236,143.451,169.236z M74.951,167.236c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5	s2.5,1.119,2.5,2.5v4.073C77.451,166.117,76.332,167.236,74.951,167.236z M88.951,165.2c-1.381,0-2.5-1.119-2.5-2.5v-4.073	c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C91.451,164.08,90.332,165.2,88.951,165.2z M59.826,165.2	c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C62.326,164.08,61.207,165.2,59.826,165.2z	 M126.451,164.2c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073	C128.951,163.08,127.832,164.2,126.451,164.2z M106.451,159.534c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5	s2.5,1.119,2.5,2.5v4.073C108.951,158.414,107.832,159.534,106.451,159.534z M151.201,155.46c-1.381,0-2.5-1.119-2.5-2.5v-4.073	c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C153.701,154.341,152.582,155.46,151.201,155.46z M67.201,153.46	c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073C69.701,152.341,68.582,153.46,67.201,153.46	z M167.701,151.387c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v4.073	C170.201,150.268,169.082,151.387,167.701,151.387z M50.701,149.387c-1.381,0-2.5-1.119-2.5-2.5v-4.073c0-1.381,1.119-2.5,2.5-2.5	s2.5,1.119,2.5,2.5v4.073C53.201,148.268,52.082,149.387,50.701,149.387z M134.951,137.106c-9.451,0-17.201-8.604-17.721-19.406	c-0.036-0.464-0.055-0.933-0.055-1.406c0-3.239,0.87-6.277,2.39-8.896c0.037-0.069,0.077-0.137,0.119-0.202	c3.106-5.193,8.787-8.678,15.267-8.678c9.802,0,17.775,7.974,17.775,17.775c0,0.474-0.019,0.942-0.055,1.406	C152.153,128.502,144.403,137.106,134.951,137.106z M122.221,117.374c0.551,6.54,6.05,11.693,12.73,11.693s12.18-5.153,12.73-11.693	c0.012-0.275,0.018-0.553,0.018-0.831c0-0.125,0.009-0.248,0.026-0.367c-0.016-1.82-0.415-3.55-1.119-5.113h-23.311	c-0.704,1.563-1.104,3.293-1.119,5.113c0.018,0.119,0.026,0.242,0.026,0.367C122.203,116.822,122.209,117.099,122.221,117.374z	 M127.308,106.063h15.287c-2.133-1.598-4.779-2.545-7.644-2.545S129.441,104.465,127.308,106.063z M84.779,137.106	c-9.456,0-17.208-8.612-17.721-19.423c-0.036-0.458-0.054-0.922-0.054-1.39c0-3.235,0.869-6.271,2.386-8.888	c0.039-0.074,0.082-0.146,0.127-0.217c3.107-5.189,8.786-8.671,15.262-8.671c9.801,0,17.775,7.974,17.775,17.775	c0,0.468-0.018,0.932-0.054,1.39C101.987,128.494,94.235,137.106,84.779,137.106z M72.048,117.362	c0.544,6.546,6.046,11.706,12.73,11.706s12.186-5.16,12.73-11.706c0.012-0.271,0.018-0.544,0.018-0.818	c0-0.124,0.009-0.245,0.026-0.364c-0.016-1.82-0.415-3.552-1.12-5.116h-23.31c-0.705,1.564-1.104,3.296-1.12,5.116	c0.018,0.119,0.026,0.24,0.026,0.364C72.03,116.818,72.036,117.091,72.048,117.362z M77.135,106.063h15.287	c-2.133-1.598-4.78-2.545-7.644-2.545S79.268,104.465,77.135,106.063z M134.951,119.043c-1.381,0-2.5-1.119-2.5-2.5v-0.25	c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v0.25C137.451,117.924,136.332,119.043,134.951,119.043z M84.779,119.043	c-1.381,0-2.5-1.119-2.5-2.5v-0.25c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5v0.25C87.279,117.924,86.159,119.043,84.779,119.043	z M76.611,88.078c-0.739,0-1.47-0.326-1.963-0.95c-0.856-1.084-0.671-2.656,0.413-3.512l16.338-12.901	c1.084-0.855,2.656-0.67,3.511,0.412c0.856,1.084,0.671,2.656-0.413,3.512L78.159,87.54C77.7,87.903,77.154,88.078,76.611,88.078z	 M142.632,87.039c-0.443,0-0.892-0.117-1.298-0.364l-17.782-10.823c-1.18-0.718-1.554-2.256-0.836-3.436s2.257-1.552,3.436-0.836	l17.782,10.823c1.18,0.718,1.554,2.256,0.836,3.436C144.299,86.613,143.476,87.039,142.632,87.039z';
        var myChart = echarts.init(document.getElementById(domName));
        var option = {
            color: ['#0099FF', '#ccc'],
            xAxis: {
                data: [
                    ''
                ],
                axisTick: {
                    show: false
                },
                axisLabel: {
                    interval: 0
                }
            },
            yAxis: {
                max: 100,
                splitLine: {show: false}
            },
            series: [{
                // This series represents real value.
                type: 'pictorialBar',
                name: 'realValue',
                symbol: path,
                // 'real value' series should has higher z than 'background'
                // series.
                z: 10,
                // When there is another series used as the background of
                // this series, it is recommended that assign
                // symbolBoundingData the same value in 'realValue' series
                // and 'background' series.
                symbolBoundingData: 100,
                data: [{
                    value: fatigueValue,
                    symbolClip: true
                }]
            }, {
                // This series is used as background.
                type: 'pictorialBar',
                name: 'background',
                symbol: path,
                // When a series is used as background, it is recommended
                // that assign symbolBoundingData the same value in
                // 'realValue' series and 'background' series.
                symbolBoundingData: 100,
                label: {
                    normal: {
                        show: true,
                        position: 'outside',
                        formatter: 'fatigue percentage: {c}',
                        textStyle: {
                            color: '#0099FF'
                        }
                    }
                },
                data: [{
                    value: fatigueValue,
                    // When a series is used as background and symbolClip
                    // is used in 'realValue' series, it is recommended that
                    // set animationDuration to be zero, which disables
                    // initial aniation, and keeps update animation enabled.
                    animationDuration: 0
                }]
            }]
        };

        // 为echarts对象加载数据
        myChart.setOption(option);
    }


    function getFatigue(url, startTime) {
        $.ajax({
            url: url,
            dataType: "json",
            type: "POST",
            async: true,
            data: {
                startTime: startTime
            },
            error: function (error) {
                console.log(error.responseText);
            },
            success: function (e) {
                if (e.code === 0) {
                    // console.log(e.data);
                    var packet = JSON.parse(e.data);
                    drawChart('chartFatigue', packet.fatigueValue);
                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        });
    }

    /**
     * 获取当前时间的时间戳
     * @returns {number}
     */
    function getCurrentTimestamp() {
        var timestamp = new Date().getTime();
        return timestamp;
    }

    var lastStartTime = getCurrentTimestamp();
    getFatigue('../screen/getFatigue', lastStartTime);
    setInterval(function () {
        var lastStartTime = getCurrentTimestamp();
        getFatigue('../screen/getFatigue', lastStartTime);
    }, ajaxInterval);


});








