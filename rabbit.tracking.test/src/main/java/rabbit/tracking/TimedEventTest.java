/*
 * Copyright 2012 The Rabbit Eclipse Plug-in Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package rabbit.tracking;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.junit.Test;

public class TimedEventTest extends EventTest {

  @Test(expected = NullPointerException.class)//
  public void constructorShouldThrowExceptionIfDurationIsNull() {
    create(new Instant(), null);
  }

  @Test public void shouldReturnTheDuration() throws Exception {
    Duration duration = new Duration(10);
    assertThat(create(new Instant(), duration).getDuration(), is(duration));
  }

  @Override protected final Event create(Instant instant) {
    return create(instant, Duration.ZERO);
  }

  protected TimedEvent create(Instant instant, Duration duration) {
    return new TimedEvent(instant, duration);
  }
}