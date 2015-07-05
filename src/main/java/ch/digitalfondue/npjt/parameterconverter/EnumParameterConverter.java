/**
 * Copyright (C) 2015 digitalfondue (info@digitalfondue.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.digitalfondue.npjt.parameterconverter;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import ch.digitalfondue.npjt.parameterconverter.ParameterConverter.AbstractParameterConverter;

public class EnumParameterConverter extends AbstractParameterConverter {

	@Override
	public boolean accept(Object arg, Class<?> parameterType) {
		return parameterType.isEnum();
	}

	@Override
	public void processParameter(String parameterName, Object arg, Class<?> parameterType, MapSqlParameterSource ps) {
		ps.addValue(parameterName, ((Enum<?>)arg).name());
	}

	@Override
	public int order() {
		return Integer.MAX_VALUE - 1;
	}

}
