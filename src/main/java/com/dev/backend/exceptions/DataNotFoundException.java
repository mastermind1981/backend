/**
 * Copyright 2016, owale.
 *
 * Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * code is a task implementation for crossover https://crossover.com
 * @author: waleed samy <waleedsamy634@gmail.com>
 **/

package com.dev.backend.exceptions;

/**
 * Throw exception if any expected data not come back from
 * {@link hibnerateDaoService}
 * 
 * @author waleed_samy
 * 
 */
public class DataNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of {@link DataNotFoundException}.
	 * 
	 * @param cause
	 *            Cause of the {@link Exception}
	 */
	public DataNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of {@link DataNotFoundException}.
	 * 
	 * @param message
	 *            Message to attach to the {@link Exception}
	 * @param cause
	 *            Cause of the {@link Exception}
	 */
	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of {@link DataNotFoundException}.
	 * 
	 * @param message
	 *            Message to attach to the {@link Exception}
	 */
	public DataNotFoundException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance of {@link DataNotFoundException}.
	 */
	public DataNotFoundException() {
		super();
	}
}